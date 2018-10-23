
public class Article {
	private int id;
	private String title;
	private String ArticleAbstract;
	
	public Article() {
		// TODO Auto-generated constructor stub
	}
	
	public Article(int id, String ArticleAbstract, String title) {
		this.id = id;
		this.ArticleAbstract = ArticleAbstract;
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getArticleAbstract() {
		return ArticleAbstract;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public void setArticleAbstract(String articleAbstract) {
		ArticleAbstract = articleAbstract;
	}
}
