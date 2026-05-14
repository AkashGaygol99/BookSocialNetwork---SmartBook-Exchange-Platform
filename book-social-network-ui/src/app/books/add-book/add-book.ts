import { Component } from '@angular/core';
import { BookService } from '../../service/book-service';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-add-book',
  standalone: true,
  imports: [FormsModule, RouterModule, CommonModule],
  templateUrl: './add-book.html',
  styleUrls: ['./add-book.css'],
})
export class AddBook {

  title = '';
  author = '';
  description = '';

  saving = false;

  constructor(private service: BookService, private router: Router) {}

  save() {
    if (!this.title.trim() || !this.author.trim()) {
      alert("Title and Author are required!");
      return;
    }

    this.saving = true;

    const book:any = {
      title: this.title,
      author: this.author,
      description: this.description
    };

    this.service.addBook(book).subscribe({
      next: () => {
        this.saving = false;
        alert("Book added successfully!");
        this.router.navigate(['/book-list']);
      },
      error: () => {
        this.saving = false;
        alert('Failed to save book!');
      }
    });
  }
}