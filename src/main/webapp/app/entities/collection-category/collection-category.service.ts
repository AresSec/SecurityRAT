import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ICollectionCategory } from 'app/shared/model/collection-category.model';

type EntityResponseType = HttpResponse<ICollectionCategory>;
type EntityArrayResponseType = HttpResponse<ICollectionCategory[]>;

@Injectable({ providedIn: 'root' })
export class CollectionCategoryService {
  public resourceUrl = SERVER_API_URL + 'api/collection-categories';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/collection-categories';

  constructor(protected http: HttpClient) {}

  create(collectionCategory: ICollectionCategory): Observable<EntityResponseType> {
    return this.http.post<ICollectionCategory>(this.resourceUrl, collectionCategory, { observe: 'response' });
  }

  update(collectionCategory: ICollectionCategory): Observable<EntityResponseType> {
    return this.http.put<ICollectionCategory>(this.resourceUrl, collectionCategory, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICollectionCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICollectionCategory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICollectionCategory[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
