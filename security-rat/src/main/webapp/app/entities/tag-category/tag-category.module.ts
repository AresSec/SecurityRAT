import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SecurityRatSharedModule } from 'app/shared/shared.module';
import { TagCategoryComponent } from './tag-category.component';
import { TagCategoryDetailComponent } from './tag-category-detail.component';
import { TagCategoryUpdateComponent } from './tag-category-update.component';
import { TagCategoryDeleteDialogComponent } from './tag-category-delete-dialog.component';
import { tagCategoryRoute } from './tag-category.route';

@NgModule({
  imports: [SecurityRatSharedModule, RouterModule.forChild(tagCategoryRoute)],
  declarations: [TagCategoryComponent, TagCategoryDetailComponent, TagCategoryUpdateComponent, TagCategoryDeleteDialogComponent],
  entryComponents: [TagCategoryDeleteDialogComponent]
})
export class SecurityRatTagCategoryModule {}
