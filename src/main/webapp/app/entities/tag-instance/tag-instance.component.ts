import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITagInstance } from 'app/shared/model/tag-instance.model';
import { TagInstanceService } from './tag-instance.service';
import { TagInstanceDeleteDialogComponent } from './tag-instance-delete-dialog.component';

@Component({
  selector: 'jhi-tag-instance',
  templateUrl: './tag-instance.component.html'
})
export class TagInstanceComponent implements OnInit, OnDestroy {
  tagInstances?: ITagInstance[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected tagInstanceService: TagInstanceService,
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
      this.tagInstanceService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ITagInstance[]>) => (this.tagInstances = res.body || []));
      return;
    }

    this.tagInstanceService.query().subscribe((res: HttpResponse<ITagInstance[]>) => (this.tagInstances = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTagInstances();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITagInstance): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTagInstances(): void {
    this.eventSubscriber = this.eventManager.subscribe('tagInstanceListModification', () => this.loadAll());
  }

  delete(tagInstance: ITagInstance): void {
    const modalRef = this.modalService.open(TagInstanceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tagInstance = tagInstance;
  }
}
