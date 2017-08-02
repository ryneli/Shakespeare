package com.zhenqiangli.shakespeare.data.model;

/**
 * Created by zhenqiangli on 7/26/17.
 */

public class DatabaseSchema {
  public static final class Characters {
    public static final String NAME = "characters";
    public static final class Cols {
      public static final String ID = "id";
      public static final String NAME = "name";
      public static final String ABBREV = "abbrev";
      public static final String DESCRIPTION = "description";
    }
  }

  public static final class Works {
    public static final String NAME = "works";
    public static final class Cols {
      public static final String ID = "id";
      public static final String TITLE = "title";
      public static final String LONG_TITLE = "long_title";
      public static final String YEAR = "year";
      public static final String GENRE = "genre";
      public static final String LAST_ACCESS = "last_access";
    }
  }

  public static final class Chapters {
    public static final String NAME = "chapters";
    public static final class Cols {
      public static final String ID = "id";
      public static final String ACT = "act";
      public static final String SCENE = "scene";
      public static final String DESCRIPTION = "description";
      public static final String WORK_ID = "work_id";
    }
  }

  public static final class Paragraphs {
    public static final String NAME = "paragraphs";
    public static final class Cols {
      public static final String ID = "id";
      public static final String PARAGRAPH_NUM = "paragraph_num";
      public static final String PLAIN_TEXT = "plain_text";
      public static final String CHARACTER_ID = "character_id";
      public static final String CHAPTER_ID = "chapter_id";
    }
  }
}
