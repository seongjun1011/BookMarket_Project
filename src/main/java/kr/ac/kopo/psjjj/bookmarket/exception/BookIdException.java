package kr.ac.kopo.psjjj.bookmarket.exception;

import lombok.Data;

@Data
public class BookIdException extends RuntimeException {
  private String bookId;
    public BookIdException(String bookId) {
      super(bookId);
      this.bookId = bookId;
    }
}
