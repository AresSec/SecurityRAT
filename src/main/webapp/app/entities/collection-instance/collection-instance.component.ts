import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICollectionInstance } from 'app/shared/model/collection-instance.model';
import { CollectionInstanceService } from './collection-instance.service';
import { CollectionInstanceDeleteDialogComponent } from './collection-instance-delete-dialog.component';

@Component({
  selector: 'jhi-collection-instance',
  templateUrl: './collection-instance.component.html'
})
export class CollectionInstanceComponent implements OnInit, OnDestroy {
  collectionInstances?: ICollectionInstance[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected collectionInstanceService: CollectionInstanceService,
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
      this.collectionInstanceService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ICollectionInstance[]>) => (this.collectionInstances = res.body || []));
      return;
    }

    this.collectionInstanceService
      .query()
      .subscribe((res: HttpResponse<ICollectionInstance[]>) => (this.collectionInstances = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCollectionInstances();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICollectionInstance): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCollectionInstances(): void {
    this.eventSubscriber = this.eventManager.subscribe('collectionInstanceListModification', () => this.loadAll());
  }

  delete(collectionInstance: ICollectionInstance): void {
    const modalRef = this.modalService.open(CollectionInstanceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.collectionInstance = collectionInstance;
  }
}
