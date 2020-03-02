import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IStatusColumn } from 'app/shared/model/status-column.model';
import { StatusColumnService } from './status-column.service';
import { StatusColumnDeleteDialogComponent } from './status-column-delete-dialog.component';

@Component({
  selector: 'jhi-status-column',
  templateUrl: './status-column.component.html'
})
export class StatusColumnComponent implements OnInit, OnDestroy {
  statusColumns?: IStatusColumn[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected statusColumnService: StatusColumnService,
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
      this.statusColumnService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IStatusColumn[]>) => (this.statusColumns = res.body || []));
      return;
    }

    this.statusColumnService.query().subscribe((res: HttpResponse<IStatusColumn[]>) => (this.statusColumns = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInStatusColumns();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IStatusColumn): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInStatusColumns(): void {
    this.eventSubscriber = this.eventManager.subscribe('statusColumnListModification', () => this.loadAll());
  }

  delete(statusColumn: IStatusColumn): void {
    const modalRef = this.modalService.open(StatusColumnDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.statusColumn = statusColumn;
  }
}
