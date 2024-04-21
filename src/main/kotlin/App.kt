import minio.FileUploader
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.ImageType
import org.apache.pdfbox.rendering.PDFRenderer
import org.apache.pdfbox.tools.imageio.ImageIOUtil
import java.io.File


fun main() {
    val document: PDDocument = PDDocument.load(File("src/main/resources/test.pdf"))
    val pdfRenderer = PDFRenderer(document)
        // note that the page number parameter is zero based
   document.pages.withIndex().forEach {
        val bim = pdfRenderer.renderImageWithDPI(it.index, 300f, ImageType.RGB)
        // suffix in filename will be used as the file format
        ImageIOUtil.writeImage(bim, "src/main/resources/pdfFilename-${it.index+1}.png", 300)
    }
    document.close()
    FileUploader().uploadAsJPGFromPDF("src/main/resources/pdfFilename-1.png")
}