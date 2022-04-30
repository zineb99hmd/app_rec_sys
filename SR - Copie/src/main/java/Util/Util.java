package Util;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Util {
    public static <K, V extends Comparable<? super V>> List<K> sortByValueAndGetKeys(Map<K, V> map,
                                                                                     final boolean ascending, List<K> output) {
        List<Map.Entry<K, V>> list = new ObjectArrayList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                if (ascending) {
                    return (o1.getValue()).compareTo(o2.getValue());
                } else {
                    return (o2.getValue()).compareTo(o1.getValue());
                }

            }
        });
        for (Map.Entry<K, V> entry : list) {
            output.add(entry.getKey());
        }
        return output;

    }
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, final boolean ascending) {
        List<Map.Entry<K, V>> list = new ObjectArrayList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                if (ascending) {
                    return (o1.getValue()).compareTo(o2.getValue());
                } else {
                    return (o2.getValue()).compareTo(o1.getValue());
                }

            }
        });

        Map<K, V> result = new Object2ObjectLinkedOpenHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
