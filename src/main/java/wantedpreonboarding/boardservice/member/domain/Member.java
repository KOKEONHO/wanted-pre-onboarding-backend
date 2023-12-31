package wantedpreonboarding.boardservice.member.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wantedpreonboarding.boardservice.article.domain.Article;
import wantedpreonboarding.boardservice.member.presentation.dto.request.MemberRegisterRequest;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@OneToMany(mappedBy = "writer")
	private List<Article> articles = new ArrayList<>();

	private Member(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public static Member of(MemberRegisterRequest request, String encryptPassword) {
		return new Member(request.getName(), request.getEmail(), encryptPassword);
	}
}
