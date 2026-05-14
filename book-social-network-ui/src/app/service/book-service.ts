import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  private BASE_URL = 'http://localhost:8080/books';

  constructor(private http: HttpClient) { }

  // AUTH HEADER
  private authHeaders() {
    const token = localStorage.getItem('token');
    return {
      headers: new HttpHeaders({
        Authorization: `Bearer ${token}`
      })
    };
  }

  // GET ALL BOOKS
  getAll() {
    return this.http.get<any[]>(`${this.BASE_URL}/all`, this.authHeaders());
  }

  // ADD BOOK
  addBook(book: any) {
    return this.http.post(
      `${this.BASE_URL}/create`,
      book,
      this.authHeaders()
    );
  }

  // UPDATE BOOK
  updateBook(id: number, book: any) {
    return this.http.put(
      `${this.BASE_URL}/update/${id}`,
      book,
      this.authHeaders()
    );
  }

  // DELETE BOOK (FIXED)
  borrow(id: number) {
  return this.http.post(`${this.BASE_URL}/borrow/${id}`, null, {
    ...this.authHeaders(),
    responseType: 'text'
  });
}

returnBook(id: number) {
  return this.http.post(`${this.BASE_URL}/return/${id}`, null, {
    ...this.authHeaders(),
    responseType: 'text'
  });
}

deleteBook(id: number) {
  return this.http.delete(`${this.BASE_URL}/delete/${id}`, {
    ...this.authHeaders(),
    responseType: 'text'
  });
}

  // GET BY ID (FIXED)
  getById(id: number) {
    return this.http.get<any>(`${this.BASE_URL}/${id}`, this.authHeaders());
  }

}