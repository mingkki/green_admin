package egovframework.coing.cmm;

import egovframework.coing.cmm.util.EgovProperties;

public class Globals {
    
    //OS 유형
    public static final String OS_TYPE = EgovProperties.getProperty("Globals.OsType");
    //DB 유형
    public static final String DB_TYPE = EgovProperties.getProperty("Globals.DbType");
    //메인 페이지
    public static final String MAIN_PAGE = EgovProperties.getProperty("Globals.MainPage");
    //ShellFile 경로
    public static final String SHELL_FILE_PATH = EgovProperties.getPathProperty("Globals.ShellFilePath");
    //퍼로퍼티 파일 위치
    public static final String CONF_PATH = EgovProperties.getPathProperty("Globals.ConfPath");
    //Server정보 프로퍼티 위치
    public static final String SERVER_CONF_PATH = EgovProperties.getPathProperty("Globals.ServerConfPath");
    //Client정보 프로퍼티 위치
    public static final String CLIENT_CONF_PATH = EgovProperties.getPathProperty("Globals.ClientConfPath");
    //파일포맷 정보 프로퍼티 위치
    public static final String FILE_FORMAT_PATH = EgovProperties.getPathProperty("Globals.FileFormatPath");
    
    // 운영환경
    public static final String RUN_MODE = EgovProperties.getProperty("Globals.Mode");
    public static final String ROOT_PATH = "LOC".equals(EgovProperties.getProperty("Globals.Mode"))
            ? EgovProperties.getProperty("Globals.RootPathLoc")
            : ("DEV".equals(EgovProperties.getProperty("Globals.Mode"))
                    ? EgovProperties.getProperty("Globals.RootPathDev")
                    : EgovProperties.getProperty("Globals.RootPathRel"));
    
    // 배포환경
    public static final String DISTRIBUTE_PATH = "LOC".equals(EgovProperties.getProperty("Globals.Mode"))
            ? EgovProperties.getProperty("Globals.DstbtPathLoc")
            : ("DEV".equals(EgovProperties.getProperty("Globals.Mode"))
                    ? EgovProperties.getProperty("Globals.DstbtPathDev")
                    : EgovProperties.getProperty("Globals.DstbtPathRel"));
    
    public static final String DISTRIBUTE_THEME_DIR = DISTRIBUTE_PATH + EgovProperties.getProperty("Globals.DstbtThemeDir");
    public static final String DISTRIBUTE_THEME_PATH = DISTRIBUTE_PATH + DISTRIBUTE_THEME_DIR;
    public static final String DISTRIBUTE_UPLOAD_DIR = EgovProperties.getProperty("Globals.DstbtUploadDir");
    public static final String DISTRIBUTE_UPLOAD_PATH = DISTRIBUTE_PATH + EgovProperties.getProperty("Globals.DstbtUploadPath") + DISTRIBUTE_UPLOAD_DIR;
    
    public static final String LOGIN_SESSION_NM = "LoginVO";
    
    // 원추가
    public static final String UPLOAD_DIR = EgovProperties.getProperty("Globals.UploadDir");
    public static final String UPLOAD_PATH = ROOT_PATH + UPLOAD_DIR;
    
    public static final String INTERFO_API_KEY = EgovProperties.getProperty("Globals.CoordApiKey");
    
}