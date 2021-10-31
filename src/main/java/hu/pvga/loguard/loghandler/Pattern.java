package hu.pvga.loguard.loghandler;

import org.json.JSONObject;

/**
 * Pattern Model.
 * A Pattern is a rule that matches the log lines.
 */
public class Pattern {
    String contains;
    String tag;

    public Pattern(JSONObject pattern)
    {
        this.contains = pattern.getString("contains");
        this.tag = pattern.getString("tag");
    }

    public boolean match(String line)
    {
        return line.contains(contains);
    }

    public String getTag()
    {
        return tag;
    }

    public String getContains()
    {
        return contains;
    }
}
