import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITraining } from 'app/shared/model/training.model';
import { TrainingService } from './training.service';
import { TrainingDeleteDialogComponent } from './training-delete-dialog.component';

@Component({
  selector: 'jhi-training',
  templateUrl: './training.component.html'
})
export class TrainingComponent implements OnInit, OnDestroy {
  trainings?: ITraining[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected trainingService: TrainingService,
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
      this.trainingService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ITraining[]>) => (this.trainings = res.body || []));
      return;
    }

    this.trainingService.query().subscribe((res: HttpResponse<ITraining[]>) => (this.trainings = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTrainings();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITraining): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTrainings(): void {
    this.eventSubscriber = this.eventManager.subscribe('trainingListModification', () => this.loadAll());
  }

  delete(training: ITraining): void {
    const modalRef = this.modalService.open(TrainingDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.training = training;
  }
}
