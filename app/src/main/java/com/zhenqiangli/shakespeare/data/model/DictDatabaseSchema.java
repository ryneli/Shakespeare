package com.zhenqiangli.shakespeare.data.model;

/**
 * Created by zhenqiangli on 8/7/17.
 */

public class DictDatabaseSchema {
    public static class Words {
        public static final String NAME = "words";
        public static final class Cols {
            public static final String KEYWORD = "keyword";
            public static final String PRON_EN = "pron_en";
            public static final String SOUND_EN = "sound_en";
            public static final String PRON_AM = "pron_am";
            public static final String SOUND_AM = "sound_am";
            public static final String DEFINITION = "definition";
        }
    }
}
