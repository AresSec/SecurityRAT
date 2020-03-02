import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITrainingBranchNode, TrainingBranchNode } from 'app/shared/model/training-branch-node.model';
import { TrainingBranchNodeService } from './training-branch-node.service';
import { TrainingBranchNodeComponent } from './training-branch-node.component';
import { TrainingBranchNodeDetailComponent } from './training-branch-node-detail.component';
import { TrainingBranchNodeUpdateComponent } from './training-branch-node-update.component';

@Injectable({ providedIn: 'root' })
export class TrainingBranchNodeResolve implements Resolve<ITrainingBranchNode> {
  constructor(private service: TrainingBranchNodeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITrainingBranchNode> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((trainingBranchNode: HttpResponse<TrainingBranchNode>) => {
          if (trainingBranchNode.body) {
            return of(trainingBranchNode.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TrainingBranchNode());
  }
}

export const trainingBranchNodeRoute: Routes = [
  {
    path: '',
    component: TrainingBranchNodeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.trainingBranchNode.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TrainingBranchNodeDetailComponent,
    resolve: {
      trainingBranchNode: TrainingBranchNodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.trainingBranchNode.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TrainingBranchNodeUpdateComponent,
    resolve: {
      trainingBranchNode: TrainingBranchNodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.trainingBranchNode.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TrainingBranchNodeUpdateComponent,
    resolve: {
      trainingBranchNode: TrainingBranchNodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.trainingBranchNode.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
