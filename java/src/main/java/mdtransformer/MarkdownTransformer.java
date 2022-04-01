package mdtransformer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MarkdownTransformer {
    private final TextFileHandler textFileHandler;
    private final LinkToFootnote transformations;

    public MarkdownTransformer(TextFileHandler textFileHandler, LinkToFootnote transformations) {
        this.textFileHandler = textFileHandler;
        this.transformations = transformations;
    }

    public void turnLinksIntoFootnotes(String sourceFile, String destinationFile) throws IOException {
        List<Footnote> footnotes = getFootnotesFromTextLines(sourceFile);
        writeLinkTextLinesFrom(footnotes, destinationFile);
        writeLinkAnchorLinesFrom(footnotes, destinationFile);
    }

    private List<Footnote> getFootnotesFromTextLines(String sourceFile) throws IOException {
        List<Footnote> footnotes = new ArrayList<>();
        for (var line: textFileHandler.readLines(sourceFile)) {
            footnotes.addAll(transformations.transformOfLinksIn(line));
        }
        return footnotes;
    }

    private void writeLinkAnchorLinesFrom(List<Footnote> footnotes, String destinationFile) throws IOException {
        for (var footnote: footnotes) {
            textFileHandler.appendLineToTextFile(footnote.anchor(), destinationFile);
        }
    }

    private void writeLinkTextLinesFrom(List<Footnote> footnotes, String destinationFile) throws IOException {
        for (var footnote: footnotes) {
            textFileHandler.appendLineToTextFile(footnote.textInPage(), destinationFile);
        }
    }
}
