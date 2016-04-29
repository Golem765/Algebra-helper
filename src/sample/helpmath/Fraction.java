package sample.helpmath;

/**
 * Created by golem765 on 22.04.16.
 */
public class Fraction {
    private int numerator;
    private int denominator;
    private boolean isNull = false;

    public Fraction(int numerator, int denominator)
    {
        this.numerator = numerator;
        this.denominator = denominator;
        normalize(this);
    }

    public Fraction(int numerator)
    {
        this.numerator = numerator;
        this.denominator = 1;
    }

    public Fraction multiply(Fraction fraction)
    {
        if(this.isNull())
            return new Fraction(0, 1);
        Fraction ret = new Fraction(numerator, denominator);
        ret.numerator *= fraction.numerator;
        ret.denominator *= fraction.denominator;
        normalize(ret);
        return ret;
    }

    public Fraction multiply(int n)
    {
        if(this.isNull())
            return new Fraction(0, 1);
        Fraction ret = new Fraction(numerator, denominator);
        ret.numerator*=n;
        normalize(ret);
        return ret;
    }
    public Fraction divide(Fraction fraction)
    {
        if(this.isNull())
            return new Fraction(0, 1);
        Fraction ret = new Fraction(numerator, denominator);
        ret.numerator *= fraction.denominator;
        ret.denominator *= fraction.numerator;
        normalize(ret);
        return ret;
    }
    public Fraction divide(int n)
    {
        Fraction ret = new Fraction(numerator, denominator);
        ret.denominator*=n;
        normalize(ret);
        return ret;
    }

    public Fraction add(Fraction fraction)
    {
        Fraction ret = new Fraction(numerator, denominator);
        if(ret.denominator==fraction.denominator)
        {
            ret.numerator += fraction.numerator;
        }
        else
        {
            ret.numerator = ret.numerator*fraction.denominator + fraction.numerator*ret.denominator;
            ret.denominator *= fraction.denominator;
        }
        normalize(ret);
        return ret;
    }
    public Fraction add(int n)
    {
        Fraction ret = new Fraction(numerator, denominator);
        n *= ret.denominator;
        ret.numerator += n;
        normalize(ret);
        return ret;
    }

    public Fraction substrate(Fraction fraction)
    {
        Fraction ret = new Fraction(numerator, denominator);
        if(ret.denominator==fraction.denominator)
        {
            ret.numerator -= fraction.numerator;
        }
        else
        {
            ret.numerator = ret.numerator*fraction.denominator - fraction.numerator*ret.denominator;
            ret.denominator *= fraction.denominator;
        }
        normalize(ret);
        return ret;
    }
    public Fraction substrate(int n)
    {
        Fraction ret = new Fraction(numerator, denominator);
        n *= ret.denominator;
        ret.numerator += n;
        normalize(ret);
        return ret;
    }

    public boolean isNull() {
        return isNull;
    }

    public static void normalize(Fraction fraction)
    {
        if(fraction.numerator==0) {
            fraction.isNull = true;
            fraction.denominator = 0;
            return;
        }
        else
        fraction.isNull = false;
        int nsd;
        do {
            nsd = HelpMath.getNSD(fraction.numerator, fraction.denominator);
            fraction.numerator /= nsd;
            fraction.denominator /= nsd;
        }
        while(nsd != 1);
        if((fraction.numerator<0&&fraction.denominator<0)||(fraction.numerator>=0&&fraction.denominator<0))
        {
            fraction.numerator = -fraction.numerator;
            fraction.denominator = -fraction.denominator;
        }
    }

    public int toInt()
    {
        normalize(this);
        if(isInt())
            return numerator;
        else if(numerator==0)
        return 0;
        else
        {
            throw new NumberFormatException();
        }
    }

    public boolean isInt()
    {
        return denominator == 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fraction fraction = (Fraction) o;
        normalize(this);
        normalize(fraction);
        if (numerator != fraction.numerator) return false;
        return denominator == fraction.denominator;

    }

    @Override
    public int hashCode() {
        normalize(this);
        int result = numerator;
        result = 31 * result + denominator;
        return result;
    }

    @Override
    public String toString() {
        if(this.isInt())
            return String.format("%d", this.toInt());
        else
        return String.format("%d/%d", numerator, denominator);
    }
}
