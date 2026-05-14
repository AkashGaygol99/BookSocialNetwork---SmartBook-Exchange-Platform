import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { BookService } from '../../service/book-service';

@Component({
  selector: 'app-edit-book',
  standalone: true,
  imports: [CommonModule, FormsModule,RouterModule],
  templateUrl: './edit-book.html',
  styleUrls: ['./edit-book.css'],
})
export class EditBook implements OnInit {

  id!: number;
  book: any = {
    title: '',
    author: '',
    description: ''
  };

  loading = true;
  saving = false;

  constructor(
    private route: ActivatedRoute,
    private service: BookService,
    private router: Router,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    this.loadBook();
  }

 loadBook(): void {
  this.loading = true;

  this.service.getById(this.id).subscribe({
    next: (data: any) => {
      console.log("EDIT BOOK DATA:", data);

      this.book = {
        title: data.title || '',
        author: data.author || '',
        description: data.description || ''
      };

      this.loading = false;
      this.cdr.detectChanges();
    },
    error: (err) => {
      console.log("EDIT LOAD ERROR:", err);
      this.loading = false;
      alert("Failed to load book!");
      this.router.navigate(['/books']);
    }
  });
}

  save() {
    this.saving = true;

    const updated:any = {
      title: this.book.title,
      author: this.book.author,
      description: this.book.description
    };

    this.service.updateBook(this.id, updated).subscribe(() => {
      this.saving = false;
      alert("Book updated successfully!");
      this.router.navigate(['/books']);
    });
  }
}