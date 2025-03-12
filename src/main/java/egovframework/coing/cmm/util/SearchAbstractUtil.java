package egovframework.coing.cmm.util;

import egovframework.coing.cmm.Globals;
import egovframework.coing.cmm.vo.SearchDocumentVO;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ko.KoreanAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class SearchAbstractUtil {
    
    protected Logger Logger = LoggerFactory.getLogger(SearchAbstractUtil.class);
    
    protected void addIndex(String type, String openMode, SearchDocumentVO searchDocumentVO) throws Exception {
        
        ArrayList<Document> docs = new ArrayList<Document>();
        docs.add(createDocument(searchDocumentVO));
        indexWrite(type, openMode, docs);
    }
    
    protected void addIndexes(String type, String openMode, List<SearchDocumentVO> list) throws Exception {
        System.out.println("99===>" + type + "-" + openMode + "-" + list.size());
        System.out.println(list);
        ArrayList<Document> docs = new ArrayList<Document>();
        for(int i = 0; i < list.size(); i++) {
            System.out.println("===>" + type + "-" + list.get(i).getTitle() + "-" + list.get(i).getContent());
            list.get(i).setType(type);
            docs.add(createDocument(list.get(i)));
        }
        indexWrite(type, openMode, docs);
    }
    
    protected Document createDocument(SearchDocumentVO searchDocumentVO) {
        
        // 필드 타입 설정
        FieldType fieldType1 = new FieldType();
        fieldType1.setIndexOptions(IndexOptions.DOCS);
        //fieldType1.setIndexed(true);  // 인덱스 포함 여부
        fieldType1.setStored(true);    // 검색시 출력 여부
        fieldType1.setTokenized(true);  // 필드 토큰화 여부
        //fieldType1.setIndexOptions(IndexOptions.DOCS_ONLY); // 문서만 색인
        
        FieldType fieldType2 = new FieldType();
        fieldType2.setIndexOptions(IndexOptions.DOCS);
        fieldType2.setStored(true);    // 검색시 출력 여부
        fieldType2.setTokenized(false);  // 필드 토큰화 여부
        
        Document doc = new Document();
        doc.add(new Field("TYPE", EgovStringUtil.nullConvert(searchDocumentVO.getType()), fieldType2));
        doc.add(new Field("KEY", EgovStringUtil.nullConvert(searchDocumentVO.getIndexKey()), fieldType2));
        doc.add(new Field("SITE_ID", EgovStringUtil.nullConvert(searchDocumentVO.getSiteId()), fieldType2));
        doc.add(new Field("TITLE", EgovStringUtil.nullConvert(searchDocumentVO.getTitle()), fieldType1));
        doc.add(new Field("CONTENT", Jsoup.parse(EgovStringUtil.nullConvert(searchDocumentVO.getContent())).text(), fieldType1));
        doc.add(new Field("URL", EgovStringUtil.nullConvert(searchDocumentVO.getLinkUrl()), fieldType2));
        doc.add(new Field("BOARD_ID", EgovStringUtil.nullConvert(searchDocumentVO.getBoardId()), fieldType2));
        doc.add(new Field("BOARD_WRITE_ID", EgovStringUtil.nullConvert(searchDocumentVO.getBoardWriteId()), fieldType2));
        doc.add(new Field("NAVI", EgovStringUtil.nullConvert(searchDocumentVO.getNavi()), fieldType2));
        doc.add(new Field("REG_DATE", EgovStringUtil.nullConvert(searchDocumentVO.getRegDate()), fieldType2));
        
        return doc;
    }
    
    protected void deleteIndexType(String type) throws Exception {
        
        System.out.println("인덱스 타입 삭제------> " + type);
        FSDirectory directory = getDirectory(getSearchPath() + "/" + type + "/");
        Analyzer analyzer = new KoreanAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        IndexWriter writer = new IndexWriter(directory, indexWriterConfig);
        
        Term query1 = new Term("TYPE", type);
        writer.deleteDocuments(query1);
        writer.commit();
        writer.deleteUnusedFiles();
        writer.close();
        analyzer.close();
        directory.close();
    }
    
    protected void deleteIndexKey(String type, SearchDocumentVO vo) throws Exception {
        
        System.out.println("인덱스 키삭제------> " + type + " - " + vo.getIndexKey());
        FSDirectory directory = getDirectory(getSearchPath() + "/" + type + "/");
        Analyzer analyzer = new KoreanAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        IndexWriter writer = new IndexWriter(directory, indexWriterConfig);
        
        Term query1 = new Term("KEY", vo.getIndexKey());
        writer.deleteDocuments(query1);
        
        if("BOARD".equals(type)) {
   			/*
   			//String boardId = key.substring(0, key.lastIndexOf("_"));
   	    	String bwrId = vo.getIndexKey().substring(vo.getIndexKey().lastIndexOf("_")+1, vo.getIndexKey().length());
   	    	FSDirectory directory2 = getDirectory(getSearchPath() + "/FILE/");
   	    	IndexWriter writer2 = new IndexWriter(directory2, indexWriterConfig);
       		Term query2 = new Term("BOARD_WRITE_ID", bwrId);
            writer2.deleteDocuments(query2);
            writer2.commit();
            writer2.close();
            directory2.close();
            */
        }
        
        writer.commit();
        
        writer.deleteUnusedFiles();
        writer.close();
        
        
        analyzer.close();
        directory.close();
    }
    
    protected void indexWrite(String type, String openMode, ArrayList<Document> docs) throws Exception {
        
        long begin = System.currentTimeMillis();
        
        String dirPath = getSearchPath() + "/" + type + "/";
        System.out.println(" - 인덱스 파일 경로 : " + dirPath);
        long end = System.currentTimeMillis();
        System.out.println("- 인덱스 파일 체크 : " + (end - begin) / 1000.0D);
        
        FSDirectory directory = getDirectory(dirPath);
        end = System.currentTimeMillis();
        System.out.println("- 인덱스 경로 오픈 : " + (end - begin) / 1000.0D);
        
        Analyzer analyzer = new KoreanAnalyzer();
        
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        if("CREATE".equals(openMode)) {
            indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        } else if("CREATE_OR_APPEND".equals(openMode)) {
            indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        } else {
            indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.APPEND);
        }
        
        IndexWriter writer = new IndexWriter(directory, indexWriterConfig);
        System.out.println("- 인덱스 라이터 생성 : " + (end - begin) / 1000.0D);
        System.out.println("-----------------------------------------------");
        
        
        for(int i = 0; i < docs.size(); i++) {
            Document doc = (Document) docs.get(i);
            end = System.currentTimeMillis();
            System.out.println("- 인덱스 문서 생성(" + doc.get("KEY") + ") : " + (end - begin) / 1000.0D);
            
            System.out.println("type 값 : " + type);
            Term query = new Term("KEY", doc.get("KEY"));
            System.out.println("query 값 : " + query);
            writer.deleteDocuments(query);
            System.out.println("deleteDocument실행 완료");
            if("BOARD".equals(type)) {
            	
            	/*
            	FSDirectory directory2 = getDirectory(getSearchPath() + "/FILE/");
            	IndexWriter writer2 = new IndexWriter(directory2, indexWriterConfig);
           		Term query2 = new Term("BBS_BOARD_ARTICLE_ID", doc.get("BBS_BOARD_ARTICLE_ID"));
                writer2.deleteDocuments(query2);
                writer2.commit();
                writer2.close();
                directory2.close();
                */
            }
            
            writer.addDocument(doc);
            System.out.println("addDocument실행 완료");
            System.out.println(doc.toString());
            end = System.currentTimeMillis();
            System.out.println("- 인덱스 라이터 문서추가 : " + (end - begin) / 1000.0D);
        }
        System.out.println("-----------------------------------------------");
        
        writer.commit();
        writer.close();
        end = System.currentTimeMillis();
        System.out.println("- 인덱스 라이터 종료 : " + (end - begin) / 1000.0D);
        
        analyzer.close();
        directory.close();
        end = System.currentTimeMillis();
        System.out.println("- 인덱스 파일 작성 시간 : " + (end - begin) / 1000.0D);
    }
    
    public String getSearchPath() throws Exception {
        
        return Globals.DISTRIBUTE_PATH + EgovProperties.getProperty("Globals.SearchIndexPath");
    }
    
    public FSDirectory getDirectory(String dirPath) throws IOException {
        
        File file = new File(dirPath);
        if(!file.exists()) {
            file.mkdirs();
        }
        return FSDirectory.open(Paths.get(dirPath));
    }
    
}