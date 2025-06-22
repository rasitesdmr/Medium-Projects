package com.rasitesdmr.commentservice.model;
import com.rasitesdmr.commentservice.domain.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "comments")
public class Comment extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private Long userId;
    private Long photoId;

    @Override
    public String toString() {
        return String.format("COMMENT ID : %s, TEXT : %s", this.id, this.text);
    }
}
