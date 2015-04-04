package cn.hehe9.persistent.entity;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Video implements Serializable {
	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database column video.id
	 *
	 * @mbggenerated
	 */
	private Integer id;

	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database column video.source_id
	 *
	 * @mbggenerated
	 */
	private Integer sourceId;

	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database column video.name
	 *
	 * @mbggenerated
	 */
	private String name;

	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database column video.author
	 *
	 * @mbggenerated
	 */
	private String author;

	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database column video.place
	 *
	 * @mbggenerated
	 */
	private String place;

	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database column video.birth_year
	 *
	 * @mbggenerated
	 */
	private String birthYear;

	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database column video.play_count_weekly
	 *
	 * @mbggenerated
	 */
	private String playCountWeekly;

	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database column video.play_count_total
	 *
	 * @mbggenerated
	 */
	private String playCountTotal;

	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database column video.story_line
	 *
	 * @mbggenerated
	 */
	@JsonIgnore
	private String storyLine;

	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database column video.story_line_brief
	 *
	 * @mbggenerated
	 */
	@JsonIgnore
	private String storyLineBrief;

	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database column video.poster_big_url
	 *
	 * @mbggenerated
	 */
	private String posterBigUrl;

	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database column video.poster_mid_url
	 *
	 * @mbggenerated
	 */
	private String posterMidUrl;

	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database column video.poster_small_url
	 *
	 * @mbggenerated
	 */
	private String posterSmallUrl;

	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database column video.icon_url
	 *
	 * @mbggenerated
	 */
	private String iconUrl;

	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database column video.list_page_url
	 *
	 * @mbggenerated
	 */
	private String listPageUrl;

	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database column video.update_remark
	 *
	 * @mbggenerated
	 */
	private String updateRemark;

	private String firstChar;

	private Integer rank;

	private Integer hot;

	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database column video.remark
	 *
	 * @mbggenerated
	 */
	private String remark;

	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database column video.create_time
	 *
	 * @mbggenerated
	 */
	private Date createTime;

	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database column video.modify_time
	 *
	 * @mbggenerated
	 */
	private Date modifyTime;

	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database table video
	 *
	 * @mbggenerated
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator.
	 * This method returns the value of the database column video.id
	 *
	 * @return the value of video.id
	 *
	 * @mbggenerated
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method sets the value of the database column video.id
	 *
	 * @param id the value for video.id
	 *
	 * @mbggenerated
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method returns the value of the database column video.source_id
	 *
	 * @return the value of video.source_id
	 *
	 * @mbggenerated
	 */
	public Integer getSourceId() {
		return sourceId;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method sets the value of the database column video.source_id
	 *
	 * @param sourceId the value for video.source_id
	 *
	 * @mbggenerated
	 */
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method returns the value of the database column video.name
	 *
	 * @return the value of video.name
	 *
	 * @mbggenerated
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method sets the value of the database column video.name
	 *
	 * @param name the value for video.name
	 *
	 * @mbggenerated
	 */
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method returns the value of the database column video.author
	 *
	 * @return the value of video.author
	 *
	 * @mbggenerated
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method sets the value of the database column video.author
	 *
	 * @param author the value for video.author
	 *
	 * @mbggenerated
	 */
	public void setAuthor(String author) {
		this.author = author == null ? null : author.trim();
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method returns the value of the database column video.place
	 *
	 * @return the value of video.place
	 *
	 * @mbggenerated
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method sets the value of the database column video.place
	 *
	 * @param place the value for video.place
	 *
	 * @mbggenerated
	 */
	public void setPlace(String place) {
		this.place = place == null ? null : place.trim();
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method returns the value of the database column video.birth_year
	 *
	 * @return the value of video.birth_year
	 *
	 * @mbggenerated
	 */
	public String getBirthYear() {
		return birthYear;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method sets the value of the database column video.birth_year
	 *
	 * @param birthYear the value for video.birth_year
	 *
	 * @mbggenerated
	 */
	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear == null ? null : birthYear.trim();
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method returns the value of the database column video.play_count_weekly
	 *
	 * @return the value of video.play_count_weekly
	 *
	 * @mbggenerated
	 */
	public String getPlayCountWeekly() {
		return playCountWeekly;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method sets the value of the database column video.play_count_weekly
	 *
	 * @param playCountWeekly the value for video.play_count_weekly
	 *
	 * @mbggenerated
	 */
	public void setPlayCountWeekly(String playCountWeekly) {
		this.playCountWeekly = playCountWeekly == null ? null : playCountWeekly.trim();
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method returns the value of the database column video.play_count_total
	 *
	 * @return the value of video.play_count_total
	 *
	 * @mbggenerated
	 */
	public String getPlayCountTotal() {
		return playCountTotal;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method sets the value of the database column video.play_count_total
	 *
	 * @param playCountTotal the value for video.play_count_total
	 *
	 * @mbggenerated
	 */
	public void setPlayCountTotal(String playCountTotal) {
		this.playCountTotal = playCountTotal == null ? null : playCountTotal.trim();
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method returns the value of the database column video.story_line
	 *
	 * @return the value of video.story_line
	 *
	 * @mbggenerated
	 */
	public String getStoryLine() {
		return storyLine;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method sets the value of the database column video.story_line
	 *
	 * @param storyLine the value for video.story_line
	 *
	 * @mbggenerated
	 */
	public void setStoryLine(String storyLine) {
		this.storyLine = storyLine == null ? null : storyLine.trim();
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method returns the value of the database column video.story_line_brief
	 *
	 * @return the value of video.story_line_brief
	 *
	 * @mbggenerated
	 */
	public String getStoryLineBrief() {
		return storyLineBrief;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method sets the value of the database column video.story_line_brief
	 *
	 * @param storyLineBrief the value for video.story_line_brief
	 *
	 * @mbggenerated
	 */
	public void setStoryLineBrief(String storyLineBrief) {
		this.storyLineBrief = storyLineBrief == null ? null : storyLineBrief.trim();
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method returns the value of the database column video.poster_big_url
	 *
	 * @return the value of video.poster_big_url
	 *
	 * @mbggenerated
	 */
	public String getPosterBigUrl() {
		return posterBigUrl;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method sets the value of the database column video.poster_big_url
	 *
	 * @param posterBigUrl the value for video.poster_big_url
	 *
	 * @mbggenerated
	 */
	public void setPosterBigUrl(String posterBigUrl) {
		this.posterBigUrl = posterBigUrl == null ? null : posterBigUrl.trim();
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method returns the value of the database column video.poster_mid_url
	 *
	 * @return the value of video.poster_mid_url
	 *
	 * @mbggenerated
	 */
	public String getPosterMidUrl() {
		return posterMidUrl;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method sets the value of the database column video.poster_mid_url
	 *
	 * @param posterMidUrl the value for video.poster_mid_url
	 *
	 * @mbggenerated
	 */
	public void setPosterMidUrl(String posterMidUrl) {
		this.posterMidUrl = posterMidUrl == null ? null : posterMidUrl.trim();
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method returns the value of the database column video.poster_small_url
	 *
	 * @return the value of video.poster_small_url
	 *
	 * @mbggenerated
	 */
	public String getPosterSmallUrl() {
		return posterSmallUrl;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method sets the value of the database column video.poster_small_url
	 *
	 * @param posterSmallUrl the value for video.poster_small_url
	 *
	 * @mbggenerated
	 */
	public void setPosterSmallUrl(String posterSmallUrl) {
		this.posterSmallUrl = posterSmallUrl == null ? null : posterSmallUrl.trim();
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method returns the value of the database column video.icon_url
	 *
	 * @return the value of video.icon_url
	 *
	 * @mbggenerated
	 */
	public String getIconUrl() {
		return iconUrl;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method sets the value of the database column video.icon_url
	 *
	 * @param iconUrl the value for video.icon_url
	 *
	 * @mbggenerated
	 */
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl == null ? null : iconUrl.trim();
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method returns the value of the database column video.list_page_url
	 *
	 * @return the value of video.list_page_url
	 *
	 * @mbggenerated
	 */
	public String getListPageUrl() {
		return listPageUrl;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method sets the value of the database column video.list_page_url
	 *
	 * @param listPageUrl the value for video.list_page_url
	 *
	 * @mbggenerated
	 */
	public void setListPageUrl(String listPageUrl) {
		this.listPageUrl = listPageUrl == null ? null : listPageUrl.trim();
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method returns the value of the database column video.update_remark
	 *
	 * @return the value of video.update_remark
	 *
	 * @mbggenerated
	 */
	public String getUpdateRemark() {
		return updateRemark;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method sets the value of the database column video.update_remark
	 *
	 * @param updateRemark the value for video.update_remark
	 *
	 * @mbggenerated
	 */
	public void setUpdateRemark(String updateRemark) {
		this.updateRemark = updateRemark == null ? null : updateRemark.trim();
	}

	public String getFirstChar() {
		return firstChar;
	}

	public void setFirstChar(String firstChar) {
		this.firstChar = firstChar;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getHot() {
		return hot;
	}

	public void setHot(Integer hot) {
		this.hot = hot;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method returns the value of the database column video.remark
	 *
	 * @return the value of video.remark
	 *
	 * @mbggenerated
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method sets the value of the database column video.remark
	 *
	 * @param remark the value for video.remark
	 *
	 * @mbggenerated
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method returns the value of the database column video.create_time
	 *
	 * @return the value of video.create_time
	 *
	 * @mbggenerated
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method sets the value of the database column video.create_time
	 *
	 * @param createTime the value for video.create_time
	 *
	 * @mbggenerated
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method returns the value of the database column video.modify_time
	 *
	 * @return the value of video.modify_time
	 *
	 * @mbggenerated
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method sets the value of the database column video.modify_time
	 *
	 * @param modifyTime the value for video.modify_time
	 *
	 * @mbggenerated
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}