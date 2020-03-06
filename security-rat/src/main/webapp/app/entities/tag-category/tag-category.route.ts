import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITagCategory, TagCategory } from 'app/shared/model/tag-category.model';
import { TagCategoryService } from './tag-category.service';
import { TagCategoryComponent } from './tag-category.component';
import { TagCategoryDetailComponent } from './tag-category-detail.component';
import { TagCategoryUpdateComponent } from './tag-category-update.component';

@Injectable({ providedIn: 'root' })
export class TagCategoryResolve implements Resolve<ITagCategory> {
  constructor(private service: TagCategoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITagCategory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tagCategory: HttpResponse<TagCategory>) => {
          if (tagCategory.body) {
            return of(tagCategory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TagCategory());
  }
}

export const tagCategoryRoute: Routes = [
  {
    path: '',
    component: TagCategoryComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.tagCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TagCategoryDetailComponent,
    resolve: {
      tagCategory: TagCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.tagCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TagCategoryUpdateComponent,
    resolve: {
      tagCategory: TagCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.tagCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TagCategoryUpdateComponent,
    resolve: {
      tagCategory: TagCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.tagCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
