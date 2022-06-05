import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { Profile } from 'src/app/types';

@Component({
  selector: 'app-profile-view',
  templateUrl: './profile-view.component.html',
  styleUrls: ['./profile-view.component.scss'],
})
export class ProfileViewComponent implements OnInit {

  @ViewChild('container') containerElRef: ElementRef<HTMLDivElement>;

  @Input() profile: Profile;

  constructor() { }

  ngOnInit() {}

  scrollToTop() {
    this.containerElRef.nativeElement.scrollTop = 0;
  }

}
