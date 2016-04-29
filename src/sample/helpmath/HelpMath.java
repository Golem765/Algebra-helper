package sample.helpmath;

/**
 * Created by golem765 on 22.04.16.
 */
public class HelpMath {
    public static int getNSK(int n, int m)
    {
        for(int i = 1; ; i++)
        {
            if(i%n==0)
                if(i%m==0)
                    return i;
        }
    }
    private static int max(int n, int m)
    {
        return n > m ? n : m;
    }
    public static int min(int n, int m) {return  n < m ? n : m; }
    private static int max(int... v)
    {
        int max = v[0];
        for(int i : v)
        {
            if (i > max) max = i;
        }
        return max;
    }
    private static int min(int... v)
    {
        int min = v[0];
        for(int i : v)
        {
            if (i < min) min = i;
        }
        return min;
    }
    public static int getNSD(int n, int m)
    {
        int i = min(n, m);
        if(i < 0)
            i = -i;
        for(; i > 1; i--) {
            if (n % i == 0) {
                if (m % i == 0)
                    return i;
            }
        }

        return 1;
    }

    public static int getNSD(int... values)
    {
        int i = min(values);
        if(i < 0)
            i = -i;
        for(; i > 1; i--)
        {
            boolean trues = true;
            for(int n : values)
            {
                if(n%i!=0) trues = false;
            }
            if(trues)
                return i;
        }
        return 1;
    }

    public static void normalize(int[] target)
    {
        int nsd;
        do {
            nsd = getNSD(target);
            for(int i = 0; i < target.length; i++)
            {
                target[i]/=nsd;
            }
        }
        while(nsd != 1);
    }

    public static boolean isNullRow(int[] target)
    {
        for(int i : target)
        {
            if(i!=0)
                return false;
        }
        return true;
    }
}
