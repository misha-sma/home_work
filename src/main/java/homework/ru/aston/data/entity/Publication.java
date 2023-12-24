package homework.ru.aston.data.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "d_type", discriminatorType = DiscriminatorType.STRING)
@Entity
@Table(name = "publications")
public abstract class Publication {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_publication")
	protected int idPublication;

	@Column(name = "title")
	protected String title;

	@Column(name = "id_publisher")
	protected int idPublisher;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "publications_authors", 
	    joinColumns = @JoinColumn(name = "id_publication", referencedColumnName = "id_publication"), 
	    inverseJoinColumns = @JoinColumn(name = "id_author", referencedColumnName = "id_author"))
	protected List<Author> authors;
}
