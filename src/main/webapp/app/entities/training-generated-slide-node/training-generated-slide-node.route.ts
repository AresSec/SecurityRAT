import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITrainingGeneratedSlideNode, TrainingGeneratedSlideNode } from 'app/shared/model/training-generated-slide-node.model';
import { TrainingGeneratedSlideNodeService } from './training-generated-slide-node.service';
import { TrainingGeneratedSlideNodeComponent } from './training-generated-slide-node.component';
import { TrainingGeneratedSlideNodeDetailComponent } from './training-generated-slide-node-detail.component';
import { TrainingGeneratedSlideNodeUpdateComponent } from './training-generated-slide-node-update.component';

@Injectable({ providedIn: 'root' })
export class TrainingGeneratedSlideNodeResolve implements Resolve<ITrainingGeneratedSlideNode> {
  constructor(private service: TrainingGeneratedSlideNodeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITrainingGeneratedSlideNode> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((trainingGeneratedSlideNode: HttpResponse<TrainingGeneratedSlideNode>) => {
          if (trainingGeneratedSlideNode.body) {
            return of(trainingGeneratedSlideNode.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TrainingGeneratedSlideNode());
  }
}

export const trainingGeneratedSlideNodeRoute: Routes = [
  {
    path: '',
    component: TrainingGeneratedSlideNodeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.trainingGeneratedSlideNode.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TrainingGeneratedSlideNodeDetailComponent,
    resolve: {
      trainingGeneratedSlideNode: TrainingGeneratedSlideNodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.trainingGeneratedSlideNode.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TrainingGeneratedSlideNodeUpdateComponent,
    resolve: {
      trainingGeneratedSlideNode: TrainingGeneratedSlideNodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.trainingGeneratedSlideNode.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TrainingGeneratedSlideNodeUpdateComponent,
    resolve: {
      trainingGeneratedSlideNode: TrainingGeneratedSlideNodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.trainingGeneratedSlideNode.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
