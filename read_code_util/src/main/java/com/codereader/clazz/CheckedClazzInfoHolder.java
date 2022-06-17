package com.codereader.clazz;

import org.apache.commons.lang.StringUtils;


public class CheckedClazzInfoHolder {
    private boolean checkParent;
    private boolean checkInterface;
    private final ClazzInfo baseClassInfo;

    public CheckedClazzInfoHolder(ClazzInfo clazzInfo) {
        this.baseClassInfo = clazzInfo;
    }

    public boolean checkDone() {
        return isCheckInterface() && isCheckParent();
    }

    public ClazzInfo getBaseClassInfo() {
        return baseClassInfo;
    }

    public boolean isCheckInterface() {
        return baseClassInfo.getInterfaces().isEmpty() || checkInterface;
    }

    public boolean isCheckParent() {
        return StringUtils.isBlank(baseClassInfo.getParentClazz()) || checkParent;
    }

    public void checkParent() {
        this.checkParent = true;
    }

    public void checkInterface() {
        this.checkInterface = true;
    }

    public boolean isParentClass() {

        boolean isParentCLass = this.baseClassInfo.isParentClass();
        if (isParentCLass && !ClazzInfo.ClassType.IMPORT_CLAZZ.equals(this.baseClassInfo.getType())) {
            this.checkParent();
            this.checkInterface();
        }
        return isParentCLass;
    }

    public boolean haveThisParent(ClazzInfo current) {
        if (current == null) {
            return false;
        }
        boolean haveThisParent = this.baseClassInfo.haveThisParent(current);
        if (haveThisParent) {
            if (ClazzInfo.ClassType.INTERFACE.equals(current.getType())) {
                this.checkInterface();
            } else {
                this.checkParent();
            }
        }
        return haveThisParent;
    }
}
