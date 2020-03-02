import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { ProjectTypeUpdateComponent } from 'app/entities/project-type/project-type-update.component';
import { ProjectTypeService } from 'app/entities/project-type/project-type.service';
import { ProjectType } from 'app/shared/model/project-type.model';

describe('Component Tests', () => {
  describe('ProjectType Management Update Component', () => {
    let comp: ProjectTypeUpdateComponent;
    let fixture: ComponentFixture<ProjectTypeUpdateComponent>;
    let service: ProjectTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [ProjectTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProjectTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProjectType(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProjectType();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
