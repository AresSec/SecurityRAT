import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITagInstance, TagInstance } from 'app/shared/model/tag-instance.model';
import { TagInstanceService } from './tag-instance.service';
import { TagInstanceComponent } from './tag-instance.component';
import { TagInstanceDetailComponent } from './tag-instance-detail.component';
import { TagInstanceUpdateComponent } from './tag-instance-update.component';

@Injectable({ providedIn: 'root' })
export class TagInstanceResolve implements Resolve<ITagInstance> {
  constructor(private service: TagInstanceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITagInstance> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tagInstance: HttpResponse<TagInstance>) => {
          if (tagInstance.body) {
            return of(tagInstance.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TagInstance());
  }
}

export const tagInstanceRoute: Routes = [
  {
    path: '',
    component: TagInstanceComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.tagInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TagInstanceDetailComponent,
    resolve: {
      tagInstance: TagInstanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.tagInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TagInstanceUpdateComponent,
    resolve: {
      tagInstance: TagInstanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.tagInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TagInstanceUpdateComponent,
    resolve: {
      tagInstance: TagInstanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.tagInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
