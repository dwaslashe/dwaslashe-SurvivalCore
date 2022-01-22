package xyz.dwaslashe.survivalcore.utils;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimerApi {

    private static final LinkedHashMap<Integer, String> values;
    static {
        (values = new LinkedHashMap(6)).put(31104000, "y");
        values.put(2592000, "msc");
        values.put(86400, "d");
        values.put(3600, "h");
        values.put(60, "min");
        values.put(1, "s");
    }
    public static String getDurationBreakdownShort(long millis) {
        if (millis == 0L) {
            return "0s";
        } else {
            long days = TimeUnit.MILLISECONDS.toDays(millis);
            if (days > 0L) {
                millis -= TimeUnit.DAYS.toMillis(days);
            }

            long hours = TimeUnit.MILLISECONDS.toHours(millis);
            if (hours > 0L) {
                millis -= TimeUnit.HOURS.toMillis(hours);
            }

            long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
            if (minutes > 0L) {
                millis -= TimeUnit.MINUTES.toMillis(minutes);
            }

            long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
            if (seconds > 0L) {
                long var1000 = millis - TimeUnit.SECONDS.toMillis(seconds);
            }

            StringBuilder sb = new StringBuilder();
            if (days > 0L) {
                sb.append(days);
                sb.append("d");
            }

            if (hours > 0L) {
                sb.append(hours);
                sb.append("h");
            }

            if (minutes > 0L) {
                sb.append(minutes);
                sb.append("m");
            }

            if (seconds > 0L) {
                sb.append(seconds);
                sb.append("s");
            }

            return sb.toString();
        }
    }

    public static ZonedDateTime getZoneDate(String zone) {
        ZoneId id = ZoneId.of(zone);
        return ZonedDateTime.now(id);
    }

    public static String getTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String t = dateFormat.format(date);
        return t;
    }

    public static String getDate(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        String t = dateFormat.format(date);
        return t;
    }
    public static long getTime(String string) {
        if (string != null && !string.isEmpty()) {
            Stack type = new Stack();
            StringBuilder value = new StringBuilder();
            boolean calc = false;
            long time = 0L;
            char[] charArray;
            int length = (charArray = string.toCharArray()).length;

            for (int j = 0; j < length; ++j) {
                char c = charArray[j];
                switch (c) {
                    case 'd':
                    case 'h':
                    case 'm':
                    case 's':
                        if (!calc) {
                            type.push(c);
                            calc = true;
                        }

                        if (calc) {
                            try {
                                long i = (long) Integer.valueOf(value.toString());
                                switch ((Character) type.pop()) {
                                    case 'd':
                                        time += i * 86400000L;
                                        break;
                                    case 'h':
                                        time += i * 3600000L;
                                        break;
                                    case 'm':
                                        time += i * 60000L;
                                        break;
                                    case 's':
                                        time += i * 1000L;
                                }
                            } catch (NumberFormatException var12) {
                                return time;
                            }
                        }

                        type.push(c);
                        calc = true;
                        break;
                    default:
                        value.append(c);
                }
            }

            return time;
        } else {
            return 0L;
        }
    }

    public static String getDay(long time) {
        Date date = new Date(time);
        SimpleDateFormat dt = new SimpleDateFormat("E");
        String st = dt.format(date.getTime());
        st = st.replace("Pn", "Poniedzialek");
        st = st.replace("Wt", "Wtorek");
        st = st.replace("Śr", "Sroda");
        st = st.replace("Cz", "Czwartek");
        st = st.replace("Pt", "Piatek");
        st = st.replace("So", "Sobota");
        st = st.replace("N", "Niedziela");
        st = st.replace("Sun", "Niedziela");
        st = st.replace("Mon", "Poniedzialek");
        st = st.replace("Tue", "Wtorek");
        st = st.replace("Wed", "Sroda");
        st = st.replace("Thu", "Czwartek");
        st = st.replace("Fri", "Piatek");
        st = st.replace("Sat", "Sobota");
        return st;
    }

    public static long parseDateDiff(String time, boolean future) {
        try {
            Pattern timePattern = Pattern.compile("(?:([0-9]+)\\s*y[a-z]*[,\\s]*)?(?:([0-9]+)\\s*mo[a-z]*[,\\s]*)?(?:([0-9]+)\\s*w[a-z]*[,\\s]*)?(?:([0-9]+)\\s*d[a-z]*[,\\s]*)?(?:([0-9]+)\\s*h[a-z]*[,\\s]*)?(?:([0-9]+)\\s*m[a-z]*[,\\s]*)?(?:([0-9]+)\\s*(?:s[a-z]*)?)?", 2);
            Matcher m = timePattern.matcher(time);
            int years = 0;
            int months = 0;
            int weeks = 0;
            int days = 0;
            int hours = 0;
            int minutes = 0;
            int seconds = 0;
            boolean found = false;

            while(m.find()) {
                if (m.group() != null && !m.group().isEmpty()) {
                    for(int i = 0; i < m.groupCount(); ++i) {
                        if (m.group(i) != null && !m.group(i).isEmpty()) {
                            found = true;
                            break;
                        }
                    }

                    if (found) {
                        if (m.group(1) != null && !m.group(1).isEmpty()) {
                            years = Integer.parseInt(m.group(1));
                        }

                        if (m.group(2) != null && !m.group(2).isEmpty()) {
                            months = Integer.parseInt(m.group(2));
                        }

                        if (m.group(3) != null && !m.group(3).isEmpty()) {
                            weeks = Integer.parseInt(m.group(3));
                        }

                        if (m.group(4) != null && !m.group(4).isEmpty()) {
                            days = Integer.parseInt(m.group(4));
                        }

                        if (m.group(5) != null && !m.group(5).isEmpty()) {
                            hours = Integer.parseInt(m.group(5));
                        }

                        if (m.group(6) != null && !m.group(6).isEmpty()) {
                            minutes = Integer.parseInt(m.group(6));
                        }

                        if (m.group(7) != null && !m.group(7).isEmpty()) {
                            seconds = Integer.parseInt(m.group(7));
                        }
                        break;
                    }
                }
            }

            if (!found) {
                return -1L;
            } else {
                Calendar c = new GregorianCalendar();
                if (years > 0) {
                    c.add(1, years * (future ? 1 : -1));
                }

                if (months > 0) {
                    c.add(2, months * (future ? 1 : -1));
                }

                if (weeks > 0) {
                    c.add(3, weeks * (future ? 1 : -1));
                }

                if (days > 0) {
                    c.add(5, days * (future ? 1 : -1));
                }

                if (hours > 0) {
                    c.add(11, hours * (future ? 1 : -1));
                }

                if (minutes > 0) {
                    c.add(12, minutes * (future ? 1 : -1));
                }

                if (seconds > 0) {
                    c.add(13, seconds * (future ? 1 : -1));
                }

                Calendar max = new GregorianCalendar();
                max.add(1, 10);
                return c.after(max) ? max.getTimeInMillis() : c.getTimeInMillis();
            }
        } catch (Exception var14) {
            return -1L;
        }
    }
    public static String secondsToString(long l){
        int seconds = (int)((l - System.currentTimeMillis()) / 1000);
        String string = "";
        for(Iterator var = values.entrySet().iterator(); var.hasNext();){
            Map.Entry<Integer, String> e = (Map.Entry) var.next();
            int iDiv = seconds / (Integer) e.getKey();
            if(iDiv >= 1) {
                int x = (int) Math.floor((double) iDiv);
                string = string + String.valueOf(x) + (String) e.getValue();
                seconds -= x * (Integer) e.getKey();
            } else {
                if(e.getValue().equalsIgnoreCase("s")) {
                    string = string + "0" + e.getValue();
                }
            }
        }
        return string;
    }
}
