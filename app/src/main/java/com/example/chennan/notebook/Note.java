package com.example.chennan.notebook;

import android.widget.Spinner;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by chennan on 2018/5/27.
 */

public class Note {
    private UUID mId;
    private String mTitle;
    private Date mCreateDate;
    private Date mModifyDate;
    private String mContainer;
    private Boolean isVatal;
    private String mSpinner;

    public String getSpinner() {
        return mSpinner;
    }

    public void setSpinner(String spinner) {
        mSpinner = spinner;
    }

    public Note(){
        this(UUID.randomUUID());

    }

    public Note(UUID id) {
        mId = id;
        mCreateDate=new Date();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getCreateDate() {
        return mCreateDate;
    }

    public void setCreateDate(Date createDate) {
        mCreateDate = createDate;
    }

    public String getFormattedDate() {
        return DateFormat.getDateInstance(DateFormat.FULL).format(mCreateDate);
    }

    public Date getModifyDate() {
        return mModifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        mModifyDate = modifyDate;
    }

    public String getContainer() {
        return mContainer;
    }

    public void setContainer(String container) {
        mContainer = container;
    }

    public Boolean isVatal() {
        return isVatal;
    }

    public void setVatal(Boolean vatal) {
        isVatal = vatal;
    }
}
