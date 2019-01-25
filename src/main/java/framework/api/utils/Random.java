package framework.api.utils;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;

/**
 * Created by bibdahal
 */
public class Random {
    private java.util.Random random = new java.util.Random(System.currentTimeMillis());

    public int nextInt(int n) {
        return random.nextInt(n);
    }

    public <T> List<T> randomSubList(Collection<T> aSet, int size) {
        return randomSubList(new ArrayList<T>(aSet), size);
    }

    public <T> List<T> randomSubList(List<T> aList, int size) {
        if (size <= 0) {
            return Collections.EMPTY_LIST;
        }
        int listSize = aList.size();
        int subsetSize = Math.min(listSize, size); //size of subset cannot be greater than size of set
        Set<Integer> randomIndices = pickRandomIndices(listSize, subsetSize);

        List<T> randomSubset = new ArrayList<T>(subsetSize);
        for (Integer index : randomIndices) {
            randomSubset.add(aList.get(index));
        }
        return randomSubset;
    }

    private Set<Integer> pickRandomIndices(int listSize, int numberOfIndices) {
        Preconditions.checkArgument(numberOfIndices <= listSize);
        Set<Integer> indices = new HashSet<>();
        while (indices.size() < numberOfIndices) {
            indices.add(random.nextInt(listSize));
        }
        return indices;
    }

    public static String getRandomStringOfLength(int stringLength){
        return RandomStringUtils.randomAlphanumeric(stringLength).toUpperCase();
    }
}
