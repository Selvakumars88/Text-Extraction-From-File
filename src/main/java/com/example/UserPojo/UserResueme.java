package com.example.UserPojo;



public class UserResueme {
	private String data;
	private String name;
	private String email;
	private String phone;
	private String skills;
	private String qualification;
	private String experience;
	private String summary;
	private String linkdnUrl;
	
	public UserResueme(){
		
	}
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public  String getSkills() {
		return skills;
	}
	public void setSkills(String skills) {
		this.skills = skills;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getLinkdnUrl() {
		return linkdnUrl;
	}
	public void setLinkdnUrl(String linkdnUrl) {
		this.linkdnUrl = linkdnUrl;
	}

	@Override
	public String toString() {
		return "UserResueme [data=" + data + ", name=" + name + ", email=" + email + ", phone=" + phone + ", skills="
				+ skills + ", qualification=" + qualification + ", experience=" + experience + ", summary=" + summary
				+ ", linkdnUrl=" + linkdnUrl + "]";
	}
	
	
}
