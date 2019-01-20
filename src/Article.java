public class Article {
	private String id;
	private String title;
	private String ArticleAbstract;
	private String type;
	
	public Article() {
		// TODO Auto-generated constructor stub
	}
	
	public Article(String id, String ArticleAbstract, String title, String type) {
		this.id = id;
		this.ArticleAbstract = ArticleAbstract;
		this.title = title;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getArticleAbstract() {
		return ArticleAbstract;
	}
	
	public String getType() {
		return type;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public void setArticleAbstract(String articleAbstract) {
		this.ArticleAbstract = articleAbstract;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
}
