import { CommonModule } from '@angular/common';
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { RouterModule, Router } from '@angular/router';
import { BookService } from '../../service/book-service';
import { Book } from '../../models/book';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-book-list',
  standalone: true,
  imports: [CommonModule, RouterModule,FormsModule],
  templateUrl: './book-list.html',
  styleUrls: ['./book-list.css'],
})
export class BookList implements OnInit {

  books: Book[] = [];
  loading = false;

  role: string | null = null;
  isAdmin = false;

  constructor(
    private service: BookService,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.role = localStorage.getItem('role');
    this.isAdmin = this.role === 'ROLE_ADMIN';
    this.load();
  }

  load(): void {
    this.loading = true;
    this.service.getAll().subscribe({
      next: (data) => {
        this.books = data;
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.loading = false;
        alert("Failed to load books!");
      }
    });
  }

  borrow(id: number): void {
    this.service.borrow(id).subscribe({
      next: () => this.load(),
      error: (err) => {
        console.log(err);
        alert(err.error?.message || err.error || 'Error while borrowing the book!');
      }
    });
  }

  returnBook(id: number): void {
    this.service.returnBook(id).subscribe({
      next: () => this.load(),
      error: () => alert("Error while returning the book!")
    });
  }

  edit(id: number): void {
    this.router.navigate(['/edit-book', id]);
  }

  delete(id: number): void {
    if (confirm("Are you sure you want to delete this book?")) {
      this.service.deleteBook(id).subscribe({
        next: () => this.load(),
        error: (err) => {
          console.log(err);
          alert(err.error?.message || err.error || "Error while deleting the book!");
        }
      });
    }
  }

  logout(): void {
    if (confirm('Are you sure you want to logout?')) {
      localStorage.removeItem('token');
      localStorage.removeItem('role');
      this.router.navigate(['/login']);
    }
  }

}