import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SecurityRatTestModule } from '../../../test.module';
import { ProjectTypeComponent } from 'app/entities/project-type/project-type.component';
import { ProjectTypeService } from 'app/entities/project-type/project-type.service';
import { ProjectType } from 'app/shared/model/project-type.model';

describe('Component Tests', () => {
  describe('ProjectType Management Component', () => {
    let comp: ProjectTypeComponent;
    let fixture: ComponentFixture<ProjectTypeComponent>;
    let service: ProjectTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [ProjectTypeComponent]
      })
        .overrideTemplate(ProjectTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProjectType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.projectTypes && comp.projectTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
