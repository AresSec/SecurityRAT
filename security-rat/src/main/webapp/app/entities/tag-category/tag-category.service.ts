import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ITagCategory } from 'app/shared/model/tag-category.model';

type EntityResponseType = HttpResponse<ITagCategory>;
type EntityArrayResponseType = HttpResponse<ITagCategory[]>;

@Injectable({ providedIn: 'root' })
export class TagCategoryService {
  public resourceUrl = SERVER_API_URL + 'api/tag-categories';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/tag-categories';

  constructor(protected http: HttpClient) {}

  create(tagCategory: ITagCategory): Observable<EntityResponseType> {
    return this.http.post<ITagCategory>(this.resourceUrl, tagCategory, { observe: 'response' });
  }

  update(tagCategory: ITagCategory): Observable<EntityResponseType> {
    return this.http.put<ITagCategory>(this.resourceUrl, tagCategory, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITagCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITagCategory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITagCategory[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
