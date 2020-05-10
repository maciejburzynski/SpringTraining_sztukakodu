package pl.maltoza.tasks.Boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.maltoza.tasks.Entity.Attachment;

@Data
@AllArgsConstructor
public class AttachmentResponse {
    String filename;
    String comment;
    static AttachmentResponse from(Attachment it){
        return new AttachmentResponse(
                it.getFilename(),
                it.getComment()
        );
    }
}
