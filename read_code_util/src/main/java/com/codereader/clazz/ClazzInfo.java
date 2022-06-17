package com.codereader.clazz;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.codereader.clazz.ClazzConstants.*;

@Data
public class ClazzInfo {

    private String clazzName;
    private String parrentClazz;
    private List<String> interfaces = new ArrayList<>();
    private ClassType type;

    public enum ClassType {
        ABSTRACT(ABSTRACT_TEG), INTERFACE(INTERFACE_TEG), SIMPLE(CLAZZ_TAG), INNER_CLASS(CLAZZ_TAG), ERROR("");
        public final String splitTag;

        ClassType(String splitTag) {
            this.splitTag = splitTag;
        }
    }


    public static ClazzInfo error() {
        ClazzInfo info = new ClazzInfo();
        info.type = ClassType.ERROR;

        return info;
    }


    public static ClazzInfo getClazzInfo(String clazzLine, ClassType type) {
        if (StringUtils.isEmpty(clazzLine)) {
            return error();
        }
        ClazzInfo info = new ClazzInfo();
        String[] split = clazzLine.split(type.splitTag);
        String preFix = "";
        String clazzInfo = split[0];
        if (split.length > 1) {
            preFix = split[0];
            clazzInfo = split[1];
        }
        info.buildClazzInfo(clazzInfo);
        info.buildPreFix(preFix);
        info.setType(type);
        return info;
    }

    //TODO build prefix
    private void buildPreFix(String preFix) {
        if (StringUtils.isEmpty(preFix)) {
            return;
        }
    }

    private void buildClazzInfo(String clazzInfo) {
        if (StringUtils.isEmpty(clazzInfo)) {
            return;
        }
        clazzInfo = clazzInfo.replaceAll(", ", ",");
        String[] infoSplit = clazzInfo.split(" ");
        this.clazzName = infoSplit[0];
        int count = 1;
        int limit = infoSplit.length;
        boolean extendTag = false;
        boolean implementsTag = false;
        while (count < limit) {
            if (infoSplit[count].equals(EXTEND_TEG) || infoSplit[count].equals(IMPLEMENTS_TEG)) {
                extendTag = infoSplit[count].equals(EXTEND_TEG);
                implementsTag = infoSplit[count].equals(IMPLEMENTS_TEG);
                count++;
                continue;
            }
            if (extendTag) {
                this.parrentClazz = infoSplit[count];
            }
            if (implementsTag) {
                this.interfaces.add(infoSplit[count]);
            }
            count++;
        }
        this.interfaces = this.interfaces.stream()
                .flatMap(data -> Arrays.stream(data.split(",")))
                .collect(Collectors.toList());
    }
}
