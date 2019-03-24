public class Article {
	private String id;
	private String title;
	private String ArticleAbstract;
	private String type;
	private String heading;
	
	public Article() {
	
	}
	
	public Article(String id, String ArticleAbstract, String title, String type, String heading) {
		this.id = id;
		this.ArticleAbstract = ArticleAbstract;
		this.title = title;
		this.type = type;
		this.heading = heading;
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
	
	public String getHeading() {
		return this.heading;
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
	
	public void setHeading(String heading) {
		this.heading = heading;
	}
	
}
