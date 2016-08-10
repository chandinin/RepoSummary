package samsao.samsaoreporter;

import java.util.Date;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cnagendra on 8/10/2016.
 */
public class RepoData {
    @SerializedName("full_name")
    private String fullName;

    @SerializedName("updated_at")
    private Date lastUpdateDate;

    @SerializedName("language")
    private String language;

    @SerializedName("default_branch")
    private String defaultBranch;

    @SerializedName("forks_count")
    private String forkCount;
    public RepoData(RepoData data)
    {
        fullName = data.fullName;
        lastUpdateDate = data.lastUpdateDate;
        language = data.language;
        defaultBranch = data.defaultBranch;
        forkCount = data.forkCount;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDefaultBranch() {
        return defaultBranch;
    }

    public void setDefaultBranch(String defaultBranch) {
        this.defaultBranch = defaultBranch;
    }


    public String getForkCount() {
        return forkCount;
    }

    public void setForkCount(String forkCount) {
        this.forkCount = forkCount;
    }

}
