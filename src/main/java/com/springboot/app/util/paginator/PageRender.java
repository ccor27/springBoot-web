package com.springboot.app.util.paginator;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PageRender<T>  {

    private String url;
    private Page<T> page;

    private int allPages;
    private int actualPage;
    private int numberElementsForPage;
    private List<PageItem> pages;

    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        this.pages= new ArrayList<PageItem>();

        numberElementsForPage=page.getSize();
        allPages=page.getTotalPages();
        actualPage=page.getNumber()+1;
        int from,to;
        if(allPages<=numberElementsForPage){
          from=1;
          to=allPages;

        }else{

            if(actualPage<=numberElementsForPage/2){
                from=1;
                to=numberElementsForPage;
            }else{
                if(actualPage>=allPages-numberElementsForPage/2){
                    from=allPages-numberElementsForPage+1;
                    to=numberElementsForPage;

                }else{
                    from=actualPage-numberElementsForPage/2;
                    to=numberElementsForPage;
                }
            }
        }

      for(int i = 0; i<to;i++){
          pages.add(new PageItem(from+i,actualPage==from+i));
      }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Page<T> getPage() {
        return page;
    }

    public void setPage(Page<T> page) {
        this.page = page;
    }

    public int getAllPages() {
        return allPages;
    }

    public void setAllPages(int allPages) {
        this.allPages = allPages;
    }

    public int getActualPage() {
        return actualPage;
    }

    public void setActualPage(int actualPage) {
        this.actualPage = actualPage;
    }

    public int getNumberElementsForPage() {
        return numberElementsForPage;
    }

    public void setNumberElementsForPage(int numberElementsForPage) {
        this.numberElementsForPage = numberElementsForPage;
    }

    public List<PageItem> getPages() {
        return pages;
    }

    public void setPages(List<PageItem> pages) {
        this.pages = pages;
    }

    public boolean isFirst(){
        return page.isFirst();
    }

    public boolean isLast(){
        return page.isLast();
    }

    public boolean isHasNext(){
        return page.hasNext();
    }
    public boolean isHasPrevious() {
        return page.hasPrevious();
    }
}
