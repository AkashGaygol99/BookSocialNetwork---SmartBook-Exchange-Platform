export interface Book {
  id: number;
  title: string;
  author: string;
  description: string;

  available: boolean;
  archived: boolean;

  ownerId?: number | null;
  borrowedById?: number | null;

  ownerName?: string | null;
  borrowedByName?: string | null;
}