package mdtransformer;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Transformations {
    public List<Footnote> linkToFootNote(String line) {
        Pattern pattern = Pattern.compile("\\[(.+?)]\\((.+?)\\)");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return List.of(new Footnote(
                    matcher.group(1) + " [^anchor1]", "[^anchor1]: " + matcher.group(2)));
        }
        return List.of();
    }
}
