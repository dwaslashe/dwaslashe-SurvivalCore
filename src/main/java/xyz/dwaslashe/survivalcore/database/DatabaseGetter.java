package xyz.dwaslashe.survivalcore.database;

import lombok.SneakyThrows;
import xyz.dwaslashe.survivalcore.helpers.StringHelper;
import xyz.dwaslashe.survivalcore.model.ObjectParser;
import xyz.dwaslashe.survivalcore.model.impl.parsers.LocationParser;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DatabaseGetter {

    private DatabaseConnector databaseConnector;

    public void setDatabaseConnector(DatabaseConnector connector){
        this.databaseConnector = connector;
    }

    @SneakyThrows
    public void createTable(String name, String primaryKey, List<String> arguments) {
        databaseConnector.getConnection().prepareStatement(String.format("CREATE TABLE IF NOT EXISTS `%s` (%s, %s)", name, String.join(", ", arguments), primaryKey)).execute();
    }

    @SneakyThrows
    public void createOrUpdate(String name, Map<String, Object> values) {
        String s = "insert into {table} ({values}) values ({?}) on duplicate key update {update}";
        StringBuilder a = new StringBuilder();
        StringBuilder b = new StringBuilder();
        StringBuilder c = new StringBuilder();

        var ref = new Object() {
            int id = 1;
        };

        for (String s1 : values.keySet()) {
            a.append(",`").append(s1).append("`");
            b.append(",?");
            c.append(", ").append(s1).append("=values(").append(s1).append(")");
        }

        a = new StringBuilder(a.toString().replaceFirst(",", ""));
        b = new StringBuilder(b.toString().replaceFirst(",", ""));
        c = new StringBuilder(c.toString().replaceFirst(", ", ""));

        s = s.replace("{table}", name)
                .replace("{values}", a.toString())
                .replace("{?}", b.toString())
                .replace("{update}", c.toString());

        PreparedStatement statement = databaseConnector.getConnection().prepareStatement(s);

        values.values().forEach(object -> ObjectParser.getParser(object).ifPresentOrElse(objectObjectParser -> {
            try {
                statement.setString(Math.min(ref.id++, values.size()), objectObjectParser.serialize(objectObjectParser.getValue()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, () -> {
            try {
                statement.setObject(Math.min(ref.id++, values.size()), object);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }));

        statement.execute();
    }
}
