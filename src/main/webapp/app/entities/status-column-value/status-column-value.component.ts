import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IStatusColumnValue } from 'app/shared/model/status-column-value.model';
import { StatusColumnValueService } from './status-column-value.service';
import { StatusColumnValueDeleteDialogComponent } from './status-column-value-delete-dialog.component';

@Component({
  selector: 'jhi-status-column-value',
  templateUrl: './status-column-value.component.html'
})
export class StatusColumnValueComponent implements OnInit, OnDestroy {
  statusColumnValues?: IStatusColumnValue[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected statusColumnValueService: StatusColumnValueService,
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
      this.statusColumnValueService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IStatusColumnValue[]>) => (this.statusColumnValues = res.body || []));
      return;
    }

    this.statusColumnValueService
      .query()
      .subscribe((res: HttpResponse<IStatusColumnValue[]>) => (this.statusColumnValues = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInStatusColumnValues();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IStatusColumnValue): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInStatusColumnValues(): void {
    this.eventSubscriber = this.eventManager.subscribe('statusColumnValueListModification', () => this.loadAll());
  }

  delete(statusColumnValue: IStatusColumnValue): void {
    const modalRef = this.modalService.open(StatusColumnValueDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.statusColumnValue = statusColumnValue;
  }
}
