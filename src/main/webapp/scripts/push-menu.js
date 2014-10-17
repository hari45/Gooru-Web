
 /*** 
  * Run this code when the #toggle-menu link has been tapped
  * or clicked
  */
function showAuthorContianer() {
 
  var $body = $( '.player' ),
      $page = $( '#page' ),
      $menu = $( '#menu' ),
 
      /* Cross browser support for CSS "transition end" event */
      transitionEnd = 'transitionend webkitTransitionEnd otransitionend MSTransitionEnd';
  $body.removeClass( 'animating left right' );
  /* When the toggle menu link is clicked, animation starts */
  $body.addClass('animating');
 
  console.log("animatin funtion..");
  /***
   * Determine the direction of the animation and
   * add the correct direction class depending
   * on whether the menu was already visible.
   */
  if ( $body.hasClass( 'menu-visible' ) ) {
   $body.addClass( 'right' );
   $body.toggleClass( 'menu-visible' );
  } else {
   $body.addClass( 'left' );
   $body.toggleClass( 'menu-visible' );
  }
  
//  $body.removeClass( 'animating left right' );
//  $body.toggleClass( 'menu-visible' );
  /***
   * When the animation (technically a CSS transition)
   * has finished, remove all animating classes and
   * either add or remove the "menu-visible" class 
   * depending whether it was visible or not previously.
   */
  /*$page.on( transitionEnd, function() {
   $body
    .removeClass( 'animating left right' )
    .toggleClass( 'menu-visible' );
 
   $page.off( transitionEnd );
  } );*/
 } 