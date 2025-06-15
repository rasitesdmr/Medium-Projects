package com.rasitesdmr.spring_boot_graphQl_monolithic.comment;

import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.audit.Auditable;
import com.rasitesdmr.spring_boot_graphQl_monolithic.photo.Photo;
import com.rasitesdmr.spring_boot_graphQl_monolithic.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "comments")
public class Comment extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id", nullable = false)
    private Photo photo;

    @Override
    public String toString() {
        return String.format("COMMENT ID : %s, TEXT : %s", this.id, this.text);
    }
}
