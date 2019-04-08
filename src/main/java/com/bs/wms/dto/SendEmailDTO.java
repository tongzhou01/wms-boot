package com.bs.wms.dto;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class SendEmailDTO implements Serializable {


    private static final long serialVersionUID = -4643301931633439998L;
    /**
     * 邮件发送者
     */
    private String senderAddress;

    /**
     * 邮件接收者 必须
     */
    private String[] receivesAddresses;

    /**
     * 邮件抄送人
     */
    private String[] copyReceiversAddresses;

    /**
     * Email发送的内容 必须
     */
    private String emailContent;

    /**
     * 邮件主题 必须
     */
    private String title;

    /**
     * 邮件附件
     */
    private List<File> attachFile;

    /**
     * 任务ID
     */
    private Long taskId;

    /**
     * 邮件接收用户名
     */
    private String[] receivesUserName;

    public String[] getReceivesUserName() {
        return receivesUserName;
    }

    public void setReceivesUserName(String[] receivesUserName) {
        this.receivesUserName = receivesUserName;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String[] getReceivesAddresses() {
        return receivesAddresses;
    }

    public void setReceivesAddresses(String[] receivesAddresses) {
        this.receivesAddresses = receivesAddresses;
    }

    public String[] getCopyReceiversAddresses() {
        return copyReceiversAddresses;
    }

    public void setCopyReceiversAddresses(String[] copyReceiversAddresses) {
        this.copyReceiversAddresses = copyReceiversAddresses;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<File> getAttachFile() {
        return attachFile;
    }

    public void setAttachFile(List<File> attachFile) {
        this.attachFile = attachFile;
    }
}