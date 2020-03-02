import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITrainingCategoryNode, TrainingCategoryNode } from 'app/shared/model/training-category-node.model';
import { TrainingCategoryNodeService } from './training-category-node.service';
import { TrainingCategoryNodeComponent } from './training-category-node.component';
import { TrainingCategoryNodeDetailComponent } from './training-category-node-detail.component';
import { TrainingCategoryNodeUpdateComponent } from './training-category-node-update.component';

@Injectable({ providedIn: 'root' })
export class TrainingCategoryNodeResolve implements Resolve<ITrainingCategoryNode> {
  constructor(private service: TrainingCategoryNodeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITrainingCategoryNode> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((trainingCategoryNode: HttpResponse<TrainingCategoryNode>) => {
          if (trainingCategoryNode.body) {
            return of(trainingCategoryNode.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TrainingCategoryNode());
  }
}

export const trainingCategoryNodeRoute: Routes = [
  {
    path: '',
    component: TrainingCategoryNodeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.trainingCategoryNode.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TrainingCategoryNodeDetailComponent,
    resolve: {
      trainingCategoryNode: TrainingCategoryNodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.trainingCategoryNode.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TrainingCategoryNodeUpdateComponent,
    resolve: {
      trainingCategoryNode: TrainingCategoryNodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.trainingCategoryNode.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TrainingCategoryNodeUpdateComponent,
    resolve: {
      trainingCategoryNode: TrainingCategoryNodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.trainingCategoryNode.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
