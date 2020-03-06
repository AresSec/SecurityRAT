import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICollectionCategory } from 'app/shared/model/collection-category.model';
import { CollectionCategoryService } from './collection-category.service';
import { CollectionCategoryDeleteDialogComponent } from './collection-category-delete-dialog.component';

@Component({
  selector: 'jhi-collection-category',
  templateUrl: './collection-category.component.html'
})
export class CollectionCategoryComponent implements OnInit, OnDestroy {
  collectionCategories?: ICollectionCategory[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected collectionCategoryService: CollectionCategoryService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.collectionCategoryService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ICollectionCategory[]>) => (this.collectionCategories = res.body || []));
      return;
    }

    this.collectionCategoryService
      .query()
      .subscribe((res: HttpResponse<ICollectionCategory[]>) => (this.collectionCategories = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCollectionCategories();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICollectionCategory): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCollectionCategories(): void {
    this.eventSubscriber = this.eventManager.subscribe('collectionCategoryListModification', () => this.loadAll());
  }

  delete(collectionCategory: ICollectionCategory): void {
    const modalRef = this.modalService.open(CollectionCategoryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.collectionCategory = collectionCategory;
  }
}
