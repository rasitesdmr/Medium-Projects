package com.rasitesdmr.spring_boot_graphQl_monolithic.photo;

import com.rasitesdmr.spring_boot_graphQl_monolithic.comment.Comment;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.audit.Auditable;
import com.rasitesdmr.spring_boot_graphQl_monolithic.like.Like;
import com.rasitesdmr.spring_boot_graphQl_monolithic.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "photos")
public class Photo extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "photo", fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(mappedBy = "photo", fetch = FetchType.LAZY)
    private List<Like> likes;

    @Override
    public String toString() {
        return String.format("PHOTO ID : %s", this.id);
    }
}
